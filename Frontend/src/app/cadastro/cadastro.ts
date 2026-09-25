import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface CadastroModel {
  id: number;
  nome: string;
  email: string;
  senha: string;
  CPF: string;
  endereco: string;
  telefone: string;
}
@Component({
  selector: 'app-cadastro',
  imports: [
    ReactiveFormsModule, 
    MatButtonModule, 
    MatFormFieldModule, 
    MatInputModule,
    CommonModule],
  templateUrl: './cadastro.html',
  styleUrl: './cadastro.css',
  standalone: true
})

export class Cadastro implements OnInit {
  cadastroForm!: FormGroup;
  listarUsuarios: CadastroModel[] = [];
  cadastroEmEdicao: CadastroModel | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario();
    this.cadastroForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => {
      console.log(res);
    });
    this.carregarUsuarios();
  }

  private carregarUsuarios(): void {
    this.httpClient.get<CadastroModel[]>('http://localhost:8081/usuarios').subscribe({
      next: (res) => this.listarUsuarios = res,
      error: () => this.listarUsuarios = []
    });
  }

  private criarFormulario(): void {
    this.cadastroForm = this.formBuilder.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      senha: ['', Validators.required],
      CPF: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
      endereco: ['', Validators.required],
      telefone: ['', Validators.required],
    });
  }

  cancelarEdicao(): void {
    this.cadastroEmEdicao = null;
    this.cadastroForm.reset();
  }

  enviarDados(): void {
    console.log(this.cadastroForm.valid);
    console.log(this.cadastroForm.getRawValue());
    
    if (this.cadastroForm.valid) {
      const dados = this.cadastroForm.getRawValue();
      const requisicao = this.cadastroEmEdicao
        ? this.httpClient.put(`http://localhost:8081/usuarios/${this.cadastroEmEdicao.id}`, dados)
        : this.httpClient.post('http://localhost:8081/usuarios', dados);

      requisicao.subscribe(() => {
        this.carregarUsuarios();
        this.cancelarEdicao();
      });
    }
  }

  editarCadastro(categoria: CadastroModel): void {
    this.cadastroEmEdicao = categoria;
    this.cadastroForm.patchValue(categoria);
  }


  excluirCategoria(categoria: CadastroModel): void {
    if (confirm(`Excluir a categoria "${categoria.nome}"?`)) {
      this.httpClient.delete(`http://localhost:8081/usuarios/${categoria.id}`).subscribe(() => {
        this.carregarUsuarios();
        if (this.cadastroEmEdicao?.id === categoria.id) {
          this.cancelarEdicao();
        }
      });
    }
  }
}
