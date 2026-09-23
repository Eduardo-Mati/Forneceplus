import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';

interface CategoriaModel {
  idCategoria: number;
  nomeCategoria: string;
  descricao: string;
  status: string;
}

@Component({
  selector: 'app-categoria',
  imports: [CommonModule, ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './categoria.html',
  styleUrl: './categoria.css',
  standalone: true
})
export class Categoria implements OnInit {
  categoriaForm!: FormGroup;
  listarCategorias: CategoriaModel[] = [];
  categoriaEmEdicao: CategoriaModel | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario();
    this.categoriaForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => {
      console.log(res);
    });

    this.carregarCategorias();
  }

  private carregarCategorias(): void {
    this.httpClient.get<CategoriaModel[]>('http://localhost:8081/categorias').subscribe({
      next: (res) => this.listarCategorias = res,
      error: () => this.listarCategorias = []
    });
  }

  private criarFormulario(): void {
    this.categoriaForm = this.formBuilder.group({
      nomeCategoria: ['', Validators.required],
      descricao: ['', Validators.required],
      status: ['', Validators.required],
    });
  }

  enviarDados(): void {
    console.log(this.categoriaForm.valid);
    console.log(this.categoriaForm.getRawValue());
    
    if (this.categoriaForm.valid) {
      const dados = this.categoriaForm.getRawValue();
      const requisicao = this.categoriaEmEdicao
        ? this.httpClient.put(`http://localhost:8081/categorias/${this.categoriaEmEdicao.idCategoria}`, dados)
        : this.httpClient.post('http://localhost:8081/categorias', dados);

      requisicao.subscribe(() => {
        this.carregarCategorias();
        this.cancelarEdicao();
      });
    }
  }

  editarCategoria(categoria: CategoriaModel): void {
    this.categoriaEmEdicao = categoria;
    this.categoriaForm.patchValue(categoria);
  }

  excluirCategoria(categoria: CategoriaModel): void {
    if (confirm(`Excluir a categoria "${categoria.nomeCategoria}"?`)) {
      this.httpClient.delete(`http://localhost:8081/categorias/${categoria.idCategoria}`).subscribe(() => {
        this.carregarCategorias();
        if (this.categoriaEmEdicao?.idCategoria === categoria.idCategoria) {
          this.cancelarEdicao();
        }
      });
    }
  }

  cancelarEdicao(): void {
    this.categoriaEmEdicao = null;
    this.categoriaForm.reset();
  }
}
