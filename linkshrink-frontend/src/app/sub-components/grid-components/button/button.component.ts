import { Component, EventEmitter, Output } from '@angular/core';
import { ICellRendererAngularComp } from 'ag-grid-angular';
import { ICellRendererParams } from 'ag-grid-community';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './button.component.html',
  styleUrl: './button.component.scss',
})
export class ButtonComponent implements ICellRendererAngularComp {
  @Output() clicked: EventEmitter<any> = new EventEmitter<any>();

  agInit(params: ICellRendererParams<any, any, any>): void {}
  refresh(params: ICellRendererParams<any, any, any>): boolean {
    return true;
  }
}
