import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';

interface ItemVendaModel {
  idItemVenda: number;
  idVenda: number;
  idProduto: number;
  quantidade: number;
  preco: number;
  statusItem: string;
}

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
  standalone: true
})
export class ItemVenda implements OnInit {

  itemVendaForm!: FormGroup;
  listarItens: ItemVendaModel[] = [];
  itemEmEdicao: ItemVendaModel | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario(); // Função chamada na hora que carrega o componente

    this.itemVendaForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => { //O valuesChanges funciona como observable, logo, nele podemos fazer N coisas durante uma requisição ou alteração de dados do formulário
      console.log(res);
    });
    
    this.carregarItens();
  }

  private carregarItens(): void {
    this.httpClient.get<ItemVendaModel[]>('http://localhost:8081/itens').subscribe({
      next: (res) => this.listarItens = res,
      error: () => this.listarItens = []
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
      const formulario = this.itemVendaForm.getRawValue();
      const dados = {
        venda: { idVenda: formulario.idVenda },
        produto: { idProduto: formulario.idProduto },
        quantidade: formulario.quantidade,
        preco: formulario.preco,
        statusItem: formulario.statusItem
      };
      const requisicao = this.itemEmEdicao
        ? this.httpClient.put(`http://localhost:8081/itens/${this.itemEmEdicao.idItemVenda}`, dados)
        : this.httpClient.post('http://localhost:8081/itens', dados);

      requisicao.subscribe(() => {
        this.carregarItens();
        this.cancelarEdicao();
      });
    }
  }

  editarItem(item: ItemVendaModel): void {
    this.itemEmEdicao = item;
    this.itemVendaForm.patchValue(item);
  }

  excluirItem(item: ItemVendaModel): void {
    if (confirm(`Excluir o item ${item.idItemVenda}?`)) {
      this.httpClient.delete(`http://localhost:8081/itens/${item.idItemVenda}`).subscribe(() => {
        this.carregarItens();
        if (this.itemEmEdicao?.idItemVenda === item.idItemVenda) {
          this.cancelarEdicao();
        }
      });
    }
  }

  cancelarEdicao(): void {
    this.itemEmEdicao = null;
    this.itemVendaForm.reset();
  }
}
