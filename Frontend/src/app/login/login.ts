import { Component, OnInit } from '@angular/core';
import { FormGroup, ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface LoginModel {
  email: string;
  senha: string;
}
@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    CommonModule
],
  templateUrl: './login.html',
  styleUrl: './login.css',
  standalone: true
})

export class Login implements OnInit {
  

  loginForm!: FormGroup;
  ValidarLogin: LoginModel[] = [];

  mensagemErro = '';

  constructor( 
    private formBuilder: FormBuilder,
    private httpClient: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.criarFormulario(); //Função chamada na hora que carrega o componente
  }

  private criarFormulario(): void {
    this.loginForm = this.formBuilder.group({
      email: [ "" , Validators.required ],
      senha: [ "" ,[ Validators.required, Validators.minLength(6) ] ]
    });
  }
  
  public enviarDados(): void {
    
    if(this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }
    
    const dadosLogin = this.loginForm.getRawValue();

    this.httpClient.get<LoginModel[]>('http://localhost:8081/usuarios').subscribe({
      next: (usuarios) => {
        const usuarioEncontrado = usuarios.find(
          usuario => 
            usuario.email === dadosLogin.email && 
            usuario.senha === dadosLogin.senha
        );

        if (usuarioEncontrado) {
          console.log('Login bem-sucedido!');
          this.router.navigate(['/home']);
        } else {
          console.log('Login ou senha incorretos.');
        }
      },
      error: (error) => {
        console.error('Erro ao buscar usuários:', error);
      }
    });


  }
}
