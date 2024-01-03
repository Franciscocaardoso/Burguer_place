import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  constructor() { }

  fetchReview(): Review {

    const fetchedReview: Review = {
      id: 1,
      comment: "Muito bem temperado",
      items: [
        { id: 1, category: "GERAL", grade: 1 },
        { id: 2, category: "COMIDA", grade: 3 },
      ]
    };

    Object.keys(ReviewItemCategory).forEach((category) => {
      const index = fetchedReview.items
        .findIndex(
          (reviewItem) => reviewItem.category === category
        );

      if (index === -1) {
        fetchedReview.items.push({ category: category as ReviewItemCategoryType });
      }
    })

    return fetchedReview;
  }

  mapToReviewUI(review: Review): ReviewUI {
    return {
      ...review,
      items: review.items.map((reviewItem) => {
        const starArray = [false, false, false, false, false];

        if (typeof reviewItem.grade !== "undefined") {
          for (let i = 0; i < reviewItem.grade; i++) {
            starArray[i] = true;
          }
        }

        return {
          id: reviewItem.id,
          category: ReviewItemCategory[reviewItem.category],
          stars: starArray
        }
      })
    }
  }
}

export interface Review {
  id: number;
  comment?: string;
  items: ReviewItems[];
}

export interface ReviewUI {
  id: number;
  comment?: string;
  items: ReviewItemsUI[];
}

interface ReviewItems {
  id?: number;
  category: ReviewItemCategoryType;
  grade?: number;
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
  AMBIENTE = 'Ambiente'
}

type ReviewItemCategoryType = keyof typeof ReviewItemCategory;