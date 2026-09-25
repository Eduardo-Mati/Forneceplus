import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';

interface VendaModel {
  idVenda: number;
  valor: number;
  data: string;
  idUsuario: number;
  status: string;
  observacao: string;
  formaPagamento: string;
}

@Component({
  selector: 'app-venda',
  imports: [CommonModule, ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './venda.html',
  styleUrl: './venda.css',
  standalone: true
})
export class Venda implements OnInit {
  vendaForm!: FormGroup;
  listarVendas: VendaModel[] = [];
  vendaEmEdicao: VendaModel | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario();
    this.vendaForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => {
      console.log(res);
    });

    this.carregarVendas();
  }

  private carregarVendas(): void {
    this.httpClient.get<VendaModel[]>('http://localhost:8081/vendas').subscribe({
      next: (res) => this.listarVendas = res,
      error: () => this.listarVendas = []
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
      const formulario = this.vendaForm.getRawValue();
      const dados = {
        valor: formulario.valor,
        data: formulario.data,
        usuario: { idUsuario: formulario.idUsuario },
        status: formulario.status,
        observacao: formulario.observacao,
        formaPagamento: formulario.formaPagamento
      };
      const requisicao = this.vendaEmEdicao
        ? this.httpClient.put(`http://localhost:8081/vendas/${this.vendaEmEdicao.idVenda}`, dados)
        : this.httpClient.post('http://localhost:8081/vendas', dados);

      requisicao.subscribe(() => {
        this.carregarVendas();
        this.cancelarEdicao();
      });
    }
  }

  editarVenda(venda: VendaModel): void {
    this.vendaEmEdicao = venda;
    this.vendaForm.patchValue(venda);
  }

  excluirVenda(venda: VendaModel): void {
    if (confirm(`Excluir a venda ${venda.idVenda}?`)) {
      this.httpClient.delete(`http://localhost:8081/vendas/${venda.idVenda}`).subscribe(() => {
        this.carregarVendas();
        if (this.vendaEmEdicao?.idVenda === venda.idVenda) {
          this.cancelarEdicao();
        }
      });
    }
  }

  cancelarEdicao(): void {
    this.vendaEmEdicao = null;
    this.vendaForm.reset();
  }
}
