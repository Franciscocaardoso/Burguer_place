import { Component } from '@angular/core';
import { SvgImageComponent } from '../svg-image/svg-image.component';

@Component({
  selector: 'app-create-or-edit-review-modal',
  standalone: true,
  imports: [SvgImageComponent],
  templateUrl: './create-or-edit-review-modal.component.html',
  styleUrl: './create-or-edit-review-modal.component.css',
})
export class CreateOrEditReviewModalComponent {}
