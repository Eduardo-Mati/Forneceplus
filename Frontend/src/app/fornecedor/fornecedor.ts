import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';

interface FornecedorModel {
  idFornecedor: number;
  nomeFornecedor: string;
  emailFornecedor: string;
  descricaoFornecedor: string;
  telefoneFornecedor: string;
  enderecoFornecedor: string;
  CNPJFornecedor: string;
}

@Component({
  selector: 'app-fornecedor',
  imports: [
    CommonModule,
    ReactiveFormsModule, 
    MatButtonModule, 
    MatFormFieldModule, 
    MatInputModule
  ],
  templateUrl: './fornecedor.html',
  styleUrl: './fornecedor.css',
  standalone: true
})
export class Fornecedor implements OnInit {

  fornecedorForm!: FormGroup;
  listarFornecedores: FornecedorModel[] = [];
  fornecedorEmEdicao: FornecedorModel | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario(); // Função chamada na hora que carrega o componente

    this.fornecedorForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => {
      console.log(res);
    });

    this.carregarFornecedores();
  }

  private carregarFornecedores(): void {
    this.httpClient.get<FornecedorModel[]>('http://localhost:8081/fornecedores').subscribe({
      next: (res) => this.listarFornecedores = res,
      error: () => this.listarFornecedores = []
    });
  }
  
  private criarFormulario(): void {
    this.fornecedorForm = this.formBuilder.group({
      nomeFornecedor: ['', Validators.required],
      emailFornecedor: ['', [Validators.required, Validators.email]],
      descricaoFornecedor: ['', Validators.required],
      telefoneFornecedor: ['', Validators.required],
      enderecoFornecedor: ['', Validators.required],
      CNPJFornecedor: ['', [Validators.required, Validators.pattern(/^\d{14}$/)]],
    });  
  }

  enviarDados(): void { //Função responsável por enciar os dados para o backend
    console.log(this.fornecedorForm.valid);
    console.log(this.fornecedorForm.getRawValue());
    if (this.fornecedorForm.valid) { //url = localhost:8080/filmes      
      const dados = this.fornecedorForm.getRawValue();
      const requisicao = this.fornecedorEmEdicao
        ? this.httpClient.put(`http://localhost:8081/fornecedores/${this.fornecedorEmEdicao.idFornecedor}`, dados)
        : this.httpClient.post('http://localhost:8081/fornecedores', dados);

      requisicao.subscribe(() => {
        this.carregarFornecedores();
        this.cancelarEdicao();
      });
    }

  }

  editarFornecedor(fornecedor: FornecedorModel): void {
    this.fornecedorEmEdicao = fornecedor;
    this.fornecedorForm.patchValue(fornecedor);
  }

  excluirFornecedor(fornecedor: FornecedorModel): void {
    if (confirm(`Excluir o fornecedor "${fornecedor.nomeFornecedor}"?`)) {
      this.httpClient.delete(`http://localhost:8081/fornecedores/${fornecedor.idFornecedor}`).subscribe(() => {
        this.carregarFornecedores();
        if (this.fornecedorEmEdicao?.idFornecedor === fornecedor.idFornecedor) {
          this.cancelarEdicao();
        }
      });
    }
  }

  cancelarEdicao(): void {
    this.fornecedorEmEdicao = null;
    this.fornecedorForm.reset();
  }
}
