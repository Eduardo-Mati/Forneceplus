import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Login } from './login/login';
import { Cadastro } from './cadastro/cadastro';
import { Categoria } from './categoria/categoria';
import { ItemVenda } from './item-venda/item-venda';
import { Venda } from './venda/venda';
import { Fornecedor } from './fornecedor/fornecedor';
import { Produto } from './produto/produto';

export const routes: Routes = [
    { path: "", redirectTo: "home", pathMatch: 'full' },
    { path: "home", component: Home },
    { path: "cadastro", component: Cadastro },
    { path: "login", component: Login },
    { path: "categoria", component: Categoria },
    { path: "venda", component: Venda },
    { path: "itens", component: ItemVenda },
    { path: "fornecedor", component: Fornecedor },
    { path: "produto", component: Produto }
];
