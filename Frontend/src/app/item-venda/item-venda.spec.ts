import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemVenda } from './item-venda';

describe('ItemVenda', () => {
  let component: ItemVenda;
  let fixture: ComponentFixture<ItemVenda>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItemVenda],
    }).compileComponents();

    fixture = TestBed.createComponent(ItemVenda);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
