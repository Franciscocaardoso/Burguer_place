import { Component, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { BoardListComponent } from '../../components/board-list/board-list.component';
import {
  Board,
  BoardLocation,
  BoardLocationType,
  BoardService,
  CapacityFilter,
  FetchBoardsFilters,
  LocationFilter,
} from '../../services/board.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SvgImageComponent } from '../../components/svg-image/svg-image.component';

@Component({
  selector: 'app-available-boards',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    BreadcrumbComponent,
    BoardListComponent,
    SvgImageComponent,
    FormsModule,
  ],
  templateUrl: './available-boards.component.html',
  styleUrl: './available-boards.component.css',
})
export class AvailableBoardsComponent implements OnInit {
  private boardService: BoardService = inject(BoardService);

  private _capacityFilterItems: CapacityFilter[];
  private _locationFilterItems: LocationFilter[];

  private _boards: Board[];
  private _activeLocationFilterId: BoardLocationType | null;
  private _activeCapacityFilter: number | null;

  constructor() {
    this._boards = [];
    this._activeCapacityFilter = null;
    this._activeLocationFilterId = null;
    this._locationFilterItems = this.boardService.getAvailableLocationFilters();
    this._capacityFilterItems = this.boardService.getAvailableCapacityFilters();
  }

  ngOnInit(): void {
    this.fetchProducts();
  }

  public get capacityFilterItems() {
    return this._capacityFilterItems;
  }

  public get locationFilterItems() {
    return this._locationFilterItems;
  }

  public get boards() {
    return this._boards;
  }

  public get activeCapacityFilterValue() {
    return this._activeCapacityFilter;
  }

  public set activeCapacityFilterValue(capacity: number | null) {
    this._activeCapacityFilter = capacity;
  }

  public get activeLocationFilterId() {
    return this._activeLocationFilterId;
  }

  public set activeLocationFilterId(id: BoardLocationType | null) {
    this._activeLocationFilterId = id;
  }

  resetFilters() {
    this._activeCapacityFilter = null;
    this._activeLocationFilterId = null;
    this.fetchProducts();
  }

  applyFilters() {
    this.fetchProducts();
  }

  activateCapacityFilter(capacity: number) {
    this._activeCapacityFilter = capacity;
  }

  activateLocationFilter(id: string) {
    if (id in BoardLocation) {
      this._activeLocationFilterId = id as BoardLocationType;
    }
  }

  private fetchProducts() {
    const options: FetchBoardsFilters = {};

    if (this._activeCapacityFilter) {
      options.capacity = this._activeCapacityFilter;
    }

    if (this._activeLocationFilterId) {
      options.location = this._activeLocationFilterId;
    }

    this.boardService.fetchBoards(options).subscribe({
      next: (data) => {
        console.log(data.boards);
        this._boards = data.boards;
      },
      error: (error) => console.error(error),
      complete: () => console.log('Successfull'),
    });
  }
}
