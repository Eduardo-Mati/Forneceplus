import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-fornecedor',
  imports: [
    ReactiveFormsModule, 
    MatButtonModule, 
    MatFormFieldModule, 
    MatInputModule
  ],
  templateUrl: './fornecedor.html',
  styleUrl: './fornecedor.css',
})
export class Fornecedor implements OnInit {

  fornecedorForm!: FormGroup;
  listarFornecedores = [];

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario(); // Função chamada na hora que carrega o componente

    this.fornecedorForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => {
      console.log(res);
    });

    this.httpClient.get('http://localhost:8081/fornecedores').subscribe((res: any) => {
      this.listarFornecedores = res;
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
      this.httpClient.post("http://localhost:8081/fornecedores", this.fornecedorForm.getRawValue()).subscribe(() => {
      });
    }

  }
}
