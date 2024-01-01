import { Injectable } from '@angular/core';
import { api } from '../lib/api';
import { Observable, from, map, EMPTY } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OccupationService {
  constructor() {}

  createOccupation(dto: CreateOccupationDTO): Observable<number> {
    console.log('Creating new occupation');
    console.log(dto);

    return from(api.post('occupations', dto)).pipe(
      map((response) => {
        return response.data.id;
      })
    );
  }
}

export interface CreateOccupationDTO {
  beginOccupation: string;
  peopleCount: number;
  boardId: number;
}
