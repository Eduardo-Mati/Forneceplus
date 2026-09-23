import { Component } from '@angular/core';
import { Pessoa } from '../models/Pessoa';
import { ɵInternalFormsSharedModule } from "@angular/forms";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [ɵInternalFormsSharedModule, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

  
  nome: string = "Fulano";

  numero: number = 123;

  condicional: boolean = false;

  condicionalVerdadeira: boolean = true;
  
  lista: Pessoa[] = [
    {nome: "Fulano", idade: 20},
    {nome: "Ciclano", idade: 30},
    {nome: "Beltrano", idade: 40}
  ];

  public retornarNome(): string{
    return this.lista[0].nome + " de tal";
  }

  indefinido: undefined = undefined;

  teste = null;
}
