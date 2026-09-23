import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';

interface ProdutoModel {
  idProduto: number;
  nomeProduto: string;
  quantidade: number;
  descricao: string;
  preco: number;
  idCategoria: number;
  idFornecedor: number;
}

@Component({
  selector: 'app-produto',
  imports: [CommonModule, ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './produto.html',
  styleUrl: './produto.css',
  standalone: true
})
export class Produto implements OnInit  {

  produtoForm!: FormGroup;
  listarProdutos: ProdutoModel[] = [];
  produtoEmEdicao: ProdutoModel | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario();
    this.produtoForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => {
      console.log(res);
    });

    this.carregarProdutos();
  }

  private carregarProdutos(): void {
    this.httpClient.get<ProdutoModel[]>('http://localhost:8081/produtos').subscribe({
      next: (res) => this.listarProdutos = res,
      error: () => this.listarProdutos = []
    });
  }

  private criarFormulario(): void {
    this.produtoForm = this.formBuilder.group({
      nomeProduto: ['', Validators.required],
      quantidade: ['', Validators.required],
      descricao: ['', Validators.required],
      preco: ['', Validators.required],
      idCategoria: ['', Validators.required],
      idFornecedor: ['', Validators.required],
    });
  }

  enviarDados(): void {
    console.log(this.produtoForm.getRawValue());
    console.log(this.produtoForm.valid);
    if (this.produtoForm.valid) {
      const dados = this.produtoForm.getRawValue();
      const requisicao = this.produtoEmEdicao
        ? this.httpClient.put(`http://localhost:8081/produtos/${this.produtoEmEdicao.idProduto}`, dados)
        : this.httpClient.post('http://localhost:8081/produtos', dados);

      requisicao.subscribe(() => {
        this.carregarProdutos();
        this.cancelarEdicao();
      });
    }
  }

  editarProduto(produto: ProdutoModel): void {
    this.produtoEmEdicao = produto;
    this.produtoForm.patchValue(produto);
  }

  excluirProduto(produto: ProdutoModel): void {
    if (confirm(`Excluir o produto "${produto.nomeProduto}"?`)) {
      this.httpClient.delete(`http://localhost:8081/produtos/${produto.idProduto}`).subscribe(() => {
        this.carregarProdutos();
        if (this.produtoEmEdicao?.idProduto === produto.idProduto) {
          this.cancelarEdicao();
        }
      });
    }
  }

  cancelarEdicao(): void {
    this.produtoEmEdicao = null;
    this.produtoForm.reset();
  }
}
