import { Injectable } from '@angular/core';
import { Observable, from, map, pipe } from 'rxjs';
import { api } from '../lib/api';

@Injectable({
  providedIn: 'root',
})
export class ReviewService {
  constructor() {}

  fetchReview(occupationId: number): Observable<Review> {
    return from(api.get(`reviews/${occupationId}`)).pipe(
      map((response) => {
        const items: ReviewItems[] = this.populateReviewItems(
          response.data.topicReviews
        );

        return {
          id: response.data.id,
          comment: response.data.comment,
          topicReviews: items,
        };
      })
    );
  }

  createReview(dto: CreateReviewDTO): Observable<Review> {
    return from(api.post('reviews', { ...dto })).pipe(
      map((response) => {
        console.log(response);
        return {
          id: response.data.id,
          comment: response.data.comment,
          topicReviews: response.data.topicReviews,
        };
      })
    );
  }

  deleteReviewTopic(topicId: number): Observable<any> {
    return from(api.delete(`reviews/topics/${topicId}`)).pipe(
      map((response) => {
        console.log(response);
      })
    );
  }

  updateReviewTopic(
    topicId: number,
    dto: UpdateReviewTopicDTO
  ): Observable<any> {
    return from(api.put(`reviews/topics/${topicId}`, { ...dto })).pipe(
      map((response) => {
        console.log(response);
      })
    );
  }

  createReviewTopic(dto: CreateReviewTopicDTO): Observable<any> {
    console.log('createReviewTopic');
    console.log(dto);

    return from(api.post('reviews/topics', { ...dto })).pipe(
      map((response) => {
        console.log(response);
      })
    );
  }

  populateReviewItems(items: ReviewItems[]) {
    const _items: ReviewItems[] = items;

    Object.keys(ReviewItemCategory).forEach((category) => {
      const index = _items.findIndex((item) => item.category === category);

      if (index === -1) {
        _items.push({ category: category as ReviewItemCategoryType, grade: 0 });
      }
    });

    return _items;
  }

  mapReviewItemsUiToReviewItems(reviewUiitems: ReviewItemsUI[]) {
    const items: ReviewItems[] = reviewUiitems.map((item) => {
      const grade = item.stars.filter((selected) => selected).length;

      const category = Object.entries(ReviewItemCategory).filter(
        (categoryItem) => categoryItem[1] === item.category
      )[0][0] as ReviewItemCategoryType;

      return {
        id: item.id,
        grade,
        category,
      };
    });

    return items;
  }

  mapToReviewUI(review: Review): ReviewUI {
    return {
      ...review,
      topicReviews: review.topicReviews.map((reviewItem) => {
        const starArray = [false, false, false, false, false];

        if (typeof reviewItem.grade !== 'undefined') {
          for (let i = 0; i < reviewItem.grade; i++) {
            starArray[i] = true;
          }
        }

        return {
          id: reviewItem.id,
          category: ReviewItemCategory[reviewItem.category],
          stars: starArray,
        };
      }),
    };
  }

  mapToReview(reviewUi: ReviewUI): Review {
    const items: ReviewItems[] = this.mapReviewItemsUiToReviewItems(
      reviewUi.topicReviews
    );

    const review: Review = {
      ...reviewUi,
      topicReviews: items,
    };

    return review;
  }
}

export interface Review {
  id?: number;
  comment?: string;
  topicReviews: ReviewItems[];
}

export interface ReviewUI {
  id?: number;
  comment?: string;
  topicReviews: ReviewItemsUI[];
}

interface ReviewItems {
  id?: number;
  category: ReviewItemCategoryType;
  grade: number;
}

export interface ReviewItemsUI {
  id?: number;
  category: string;
  stars: boolean[];
}

enum ReviewItemCategory {
  GERAL = 'Avaliação geral',
  COMIDA = 'Comida',
  ATENDIMENTO = 'Atendimento',
  AMBIENTE = 'Ambiente',
}

type ReviewItemCategoryType = keyof typeof ReviewItemCategory;

export interface CreateReviewDTO {
  occupationId: number;
  comment?: string;
  items: Array<{
    grade: number;
    category: ReviewItemCategoryType;
  }>;
}

export interface UpdateReviewDTO {
  occupationId: number;
  comment?: string;
  items: Array<{
    grade: number;
    category: ReviewItemCategoryType;
  }>;
}

export interface UpdateReviewTopicDTO {
  grade: number;
}

export interface CreateReviewTopicDTO {
  reviewId: number;
  grade: number;
  category: ReviewItemCategoryType;
}
