import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface CadastroModel {
  nome: string;
  email: string;
  senha: string;
  CPF: string;
  endereco: string;
  telefone: string;
}
@Component({
  selector: 'app-cadastro',
  imports: [
    ReactiveFormsModule, 
    MatButtonModule, 
    MatFormFieldModule, 
    MatInputModule,
    CommonModule],
  templateUrl: './cadastro.html',
  styleUrl: './cadastro.css',
  standalone: true
})
export class Cadastro implements OnInit {
  cadastroForm!: FormGroup;
  listarUsuarios = [];

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.criarFormulario();
    this.cadastroForm.valueChanges.pipe(debounceTime(400), take(2)).subscribe((res) => {
      console.log(res);
    });
    this.httpClient.get('http://localhost:8081/usuarios').subscribe((res: any) => {
      this.listarUsuarios = res;
    });
    
  }

  private criarFormulario(): void {
    this.cadastroForm = this.formBuilder.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      senha: ['', Validators.required],
      CPF: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
      endereco: ['', Validators.required],
      telefone: ['', Validators.required],
    });
  }

  enviarDados(): void {
    console.log(this.cadastroForm.valid);
    console.log(this.cadastroForm.getRawValue());
    if (this.cadastroForm.valid) {
      this.httpClient.post<CadastroModel>('http://localhost:8081/usuarios', this.cadastroForm.getRawValue()).subscribe(() => {
      
      });
    }
  }
}
