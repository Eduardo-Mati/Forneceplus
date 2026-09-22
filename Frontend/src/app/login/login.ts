import { Component, OnInit } from '@angular/core';
import { FormGroup, ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';


@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule, 
    MatButtonModule, 
    MatFormFieldModule, 
    MatInputModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})

export class Login implements OnInit {
  

  loginForm!: FormGroup;

  constructor( 
    private formBuilder: FormBuilder 
  ) {}

  ngOnInit(): void {
    this.criarFormulario(); //Função chamada na hora que carrega o componente
  }

  private criarFormulario(): void {
    this.loginForm = this.formBuilder.group({
      login: [ "" , Validators.required ],
      password: [ "" , Validators.required, Validators.minLength(6), Validators.pattern(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/) ]
    });
  }
  public enviarDados(): void {
    console.log(this.loginForm.getRawValue());

    //url backend = localhost:8081/login
  }
}
