import { Component, inject } from '@angular/core';
import { SvgImageComponent } from '../svg-image/svg-image.component';
import { ReviewService, ReviewUI } from '../../services/review.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-or-edit-review-modal',
  standalone: true,
  imports: [CommonModule, SvgImageComponent],
  templateUrl: './create-or-edit-review-modal.component.html',
  styleUrl: './create-or-edit-review-modal.component.css',
})
export class CreateOrEditReviewModalComponent {
  private reviewService: ReviewService = inject(ReviewService);

  private _type: ModalType;
  private _review: ReviewUI;

  constructor() {
    this._type = "CREATE";
    this._review = this.reviewService.mapToReviewUI(
      this.reviewService.fetchReview()
    );

    console.log(this._review);
  }

  public get review() {
    return this._review;
  }

  doRate(itemIndex: number, starIndex: number) {
    const prevRate = this._review.items[itemIndex].stars.reduce((totalizer, value) => {
      return totalizer + (value ? 1 : 0)
    }, 0);
    const newRate = starIndex + 1;

    if (prevRate === newRate) {
      this._review.items[itemIndex].stars = Array.from({ length: 5 }, () => false);
      return;
    }

    const newStars = this._review.items[itemIndex].stars.map(
      (_, _starIndex) => {
        return _starIndex <= starIndex;
      }
    );

    this._review.items[itemIndex].stars = newStars;
  }

}

export type ModalType = 'CREATE' | 'EDIT';