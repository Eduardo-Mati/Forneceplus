import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-venda',
  imports: [ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './venda.html',
  styleUrl: './venda.css',
})
export class Venda implements OnInit {
  vendaForm!: FormGroup;
  listarVendas = [];

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario();
    this.vendaForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => {
      console.log(res);
    });

    this.httpClient.get('http://localhost:8081/vendas').subscribe((res: any) => {
      this.listarVendas = res;
    });
  }
  
  private criarFormulario(): void {
    this.vendaForm = this.formBuilder.group({
      valor: ['', Validators.required],
      data: ['', Validators.required],
      idUsuario: ['', Validators.required],
      status: ['', Validators.required],
      observacao: ['', Validators.required],
      formaPagamento: ['', Validators.required],
    });
    
  }

  enviarDados(): void {
    console.log(this.vendaForm.valid);
    console.log(this.vendaForm.getRawValue());
    if (this.vendaForm.valid) {
      this.httpClient.post('http://localhost:8081/vendas', this.vendaForm.getRawValue()).subscribe(() => {
      });
    }
  }
}
