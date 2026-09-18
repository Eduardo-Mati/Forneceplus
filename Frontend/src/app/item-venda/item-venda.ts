import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-item-venda',
  imports: [
    ReactiveFormsModule, 
    MatButtonModule, 
    MatFormFieldModule, 
    MatInputModule,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  templateUrl: './item-venda.html',
  styleUrl: './item-venda.css',
})
export class ItemVenda implements OnInit {

  itemVendaForm!: FormGroup;
  listarItens = [];

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario(); // Função chamada na hora que carrega o componente

    this.itemVendaForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => { //O valuesChanges funciona como observable, logo, nele podemos fazer N coisas durante uma requisição ou alteração de dados do formulário
      console.log(res);
    });
    
    this.httpClient.get('http://localhost:8081/itens').subscribe((res: any) => {
      this.listarItens = res;
    });

    this.httpClient.get('http://localhost:8081/vendas').subscribe((res: any) => {
      this.listarItens = res;
    });
  }
  private criarFormulario(): void { // Função que cria o formulario passando cada um dos campos JSON
    this.itemVendaForm = this.formBuilder.group({
      idVenda: ['', Validators.required],
      idProduto: ['', Validators.required],
      quantidade: ['', Validators.required],
      preco: ['', Validators.required],
      statusItem: ['', Validators.required],
    });

  }

  enviarDados(): void { // Função responsável por enviar os dados para o backend
    console.log(this.itemVendaForm.valid);
    console.log(this.itemVendaForm.getRawValue());
    if (this.itemVendaForm.valid) {
      this.httpClient.post('http://localhost:8081/itens', this.itemVendaForm.getRawValue()).subscribe(() => {
      });
    }
  }
}
