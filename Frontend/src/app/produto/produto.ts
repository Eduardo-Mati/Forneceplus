import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-produto',
  imports: [ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './produto.html',
  styleUrl: './produto.css',
})
export class Produto implements OnInit  {

  produtoForm!: FormGroup;
  listarProdutos = [];

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario();
    this.produtoForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => {
      console.log(res);
    });

    this.httpClient.get('http://localhost:8081/produtos').subscribe((res: any) => {
      this.listarProdutos = res;
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
      this.httpClient.post('http://localhost:8081/produtos', this.produtoForm.getRawValue()).subscribe(() => {
      });
    }
  }
}
