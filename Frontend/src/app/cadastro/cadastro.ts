import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface CadastroModel {
  idUsuario: number;
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
  mensagemErro = '';
  mensagemSucesso = '';

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
      error: (error) => {
        console.error('Erro ao carregar usuários:', error);
        this.listarUsuarios = [];
      }
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
    this.mensagemErro = '';
    this.mensagemSucesso = '';

    console.log(this.cadastroForm.valid);
    console.log(this.cadastroForm.getRawValue());
    
    if (this.cadastroForm.valid) {
      const dados = this.cadastroForm.getRawValue();
      const requisicao = this.cadastroEmEdicao
        ? this.httpClient.put(`http://localhost:8081/usuarios/${this.cadastroEmEdicao.idUsuario}`, dados)
        : this.httpClient.post('http://localhost:8081/usuarios', dados);

      requisicao.subscribe({
        next: () => {
          this.mensagemSucesso = this.cadastroEmEdicao
            ? 'Usuário atualizado com sucesso!'
            : 'Usuário adicionado com sucesso!';
          this.carregarUsuarios();
          this.cancelarEdicao();
        },
        error: (error) => {
          console.error('Erro ao salvar usuário:', error);
          this.mensagemErro = error.status === 400
            ? 'Não foi possível cadastrar. Verifique os dados informados.'
            : 'Não foi possível cadastrar o usuário. Tente novamente.';
        }
      });
    }
  }

  editarCadastro(usuario: CadastroModel): void {
    this.cadastroEmEdicao = usuario;
    this.cadastroForm.patchValue(usuario);
  }


  excluirUsuario(usuario: CadastroModel): void {
    if (confirm(`Excluir o usuário "${usuario.nome}"?`)) {
      this.httpClient.delete(`http://localhost:8081/usuarios/${usuario.idUsuario}`).subscribe({
        next: () => {
          this.carregarUsuarios();
          if (this.cadastroEmEdicao?.idUsuario === usuario.idUsuario) {
            this.cancelarEdicao();
          }
        },
        error: (error) => console.error('Erro ao excluir usuário:', error)
      });
    }
  }
}
