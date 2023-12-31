import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import {
  Board,
  BoardLocation,
  BoardLocationType,
} from '../../services/board.service';
import { SvgImageComponent } from '../svg-image/svg-image.component';

@Component({
  selector: 'app-board-list',
  standalone: true,
  imports: [CommonModule, SvgImageComponent],
  templateUrl: './board-list.component.html',
  styleUrl: './board-list.component.css',
})
export class BoardListComponent {
  private _boards: Board[] = [];

  @Input()
  public set boards(boards: Board[]) {
    this._boards = boards;
  }

  public get boards() {
    return this._boards;
  }

  getBoardLocationText(type: BoardLocationType) {
    return BoardLocation[type];
  }
}
