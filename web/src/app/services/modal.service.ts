import {
  ApplicationRef,
  ComponentRef,
  EnvironmentInjector,
  Injectable,
  createComponent,
  inject,
} from '@angular/core';
import { ModalComponent } from '../components/modal/modal.component';
import { ModalContentComponent } from '../components/modal-content/modal-content.component';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ModalService {
  private createOccupationNotifier?: Observable<number>;

  private appRef: ApplicationRef = inject(ApplicationRef);
  private injector: EnvironmentInjector = inject(EnvironmentInjector);

  public newModalComponent!: ComponentRef<ModalComponent>;

  openModal() {
    const newComponent = createComponent(ModalContentComponent, {
      environmentInjector: this.injector,
    });

    this.createOccupationNotifier =
      newComponent.instance.amountPeopleEventEmitter.asObservable();

    this.newModalComponent = createComponent(ModalComponent, {
      environmentInjector: this.injector,
      projectableNodes: [[newComponent.location.nativeElement]],
    });

    this.newModalComponent.hostView.detectChanges();

    document.body.appendChild(this.newModalComponent.location.nativeElement);

    this.appRef.attachView(newComponent.hostView);
    this.appRef.attachView(this.newModalComponent.hostView);

    return this.createOccupationNotifier;
  }

  closeModal() {
    this.newModalComponent.destroy();
  }
}
