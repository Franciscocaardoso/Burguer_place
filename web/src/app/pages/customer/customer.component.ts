import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SvgImageComponent } from '../../components/svg-image/svg-image.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import {
  Category,
  CategoryType,
  Product,
  ProductService,
} from '../../services/product.service';
import { PaginationComponent } from '../../components/pagination/pagination.component';

@Component({
  selector: 'app-customer',
  standalone: true,
  imports: [
    CommonModule,
    SvgImageComponent,
    BreadcrumbComponent,
    PaginationComponent,
  ],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.css',
})
export class CustomerComponent implements OnInit {
  public readonly IMG_BASE_URL = 'https://img.freepik.com/fotos-gratis/';

  private productService: ProductService = inject(ProductService);

  private _expandedItems: boolean[];
  private _activeMenu: CategoryType;
  private _products: Product[];
  private readonly _availableCategories: [string, Category][];
  private _totalPages: number;
  private _currentPage: number;

  constructor() {
    this._currentPage = 0;
    this._totalPages = 0;
    this._expandedItems = [false, false];
    this._activeMenu = 'BURGER';
    this._availableCategories = Object.entries(Category);
    this._products = [];
  }

  ngOnInit(): void {
    this.fetchProducts();
  }

  public get expandedItems() {
    return this._expandedItems;
  }

  public get availableCategories() {
    return this._availableCategories;
  }

  public get products() {
    return this._products;
  }

  public get totalPages() {
    return this._totalPages;
  }

  changeCurrentPage(page: number) {
    console.log(page);
    this._currentPage = page;
    this.fetchProducts();
  }

  toggleExpanded(index: number) {
    this._expandedItems = this._expandedItems.map((expanded, _index) => {
      return _index === index ? !expanded : false;
    });
  }

  changeActiveMenu(category: string) {
    if (category in Category) {
      this._activeMenu = category as CategoryType;
      this._currentPage = 0;
      this.fetchProducts();
    }
  }

  isMenuActive(categoryType: string) {
    return this._activeMenu === categoryType;
  }

  private fetchProducts() {
    this.productService
      .fetchProducts({
        category: this._activeMenu,
        page: this._currentPage,
      })
      .subscribe({
        next: (data) => {
          console.log(data);
          this._products = data.products;
          this._totalPages = data.totalPages;
        },
        error: (error) => console.error(error),
        complete: () => console.log('Successfull'),
      });
  }
}
