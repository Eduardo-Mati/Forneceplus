import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-categoria',
  imports: [ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './categoria.html',
  styleUrl: './categoria.css',
})
export class Categoria implements OnInit {
  categoriaForm!: FormGroup;
  listarCategorias = [];

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario();
    this.categoriaForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => {
      console.log(res);
    });

    this.httpClient.get('http://localhost:8081/categorias').subscribe((res: any) => {
      this.listarCategorias = res;
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
      this.httpClient.post('http://localhost:8081/categorias', this.categoriaForm.getRawValue()).subscribe(() => {
      });
    }
  }
}
