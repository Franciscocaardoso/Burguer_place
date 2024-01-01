import { Component, OnInit, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import {
  Board,
  BoardLocation,
  BoardLocationType,
  BoardService,
  CapacityFilter,
  FetchBoardsFilters,
  LocationFilter,
} from '../../services/board.service';

import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { SvgImageComponent } from '../../components/svg-image/svg-image.component';
import { ModalService } from '../../services/modal.service';
import {
  CreateOccupationDTO,
  OccupationService,
} from '../../services/occupation.service';

@Component({
  selector: 'app-available-boards',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    BreadcrumbComponent,
    SvgImageComponent,
    FormsModule,
  ],
  templateUrl: './available-boards.component.html',
  styleUrl: './available-boards.component.css',
})
export class AvailableBoardsComponent implements OnInit {
  private occupationService: OccupationService = inject(OccupationService);
  private boardService: BoardService = inject(BoardService);
  private modalService: ModalService = inject(ModalService);
  private router: Router = inject(Router);

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

  getBoardLocationText(type: BoardLocationType) {
    return BoardLocation[type];
  }

  onSelectBoard(board: Board) {
    console.log(board);
    this.modalService.openModal().subscribe({
      next: (peopleCount) => {
        this.createOccupation(board.id, peopleCount);
      },
    });
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

  private createOccupation(boardId: number, peopleCount: number) {
    if (peopleCount <= 0) {
      return alert('A quantidade de pessoas deve ser maior ou igual a 1');
    }

    const beginOccupation = new Date();
    beginOccupation.setSeconds(beginOccupation.getSeconds() - 1);

    const createOccupationDTO: CreateOccupationDTO = {
      beginOccupation: beginOccupation.toISOString(),
      boardId,
      peopleCount,
    };

    this.occupationService.createOccupation(createOccupationDTO).subscribe({
      next: (occupationId) => {
        console.log(occupationId);
        this.fetchProducts();
        this.modalService.closeModal();
        this.router.navigate([`customers/${occupationId}`]);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
}
