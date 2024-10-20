import { Component } from '@angular/core';
import { NavComponent } from '../sub-components/nav/nav.component';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef } from 'ag-grid-community';
import { ButtonComponent } from '../sub-components/grid-components/button/button.component';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NavComponent, AgGridAngular,MatIconModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  deleteRow(data: any) {
    this.rowData = this.rowData.filter((d) => d.id !== data.id);
  }

  rowData = [
    { id: 1, make: 'Tesla', model: 'Model Y', price: 64950, electric: true },
    { id: 2, make: 'Ford', model: 'F-Series', price: 33850, electric: false },
    { id: 3, make: 'Toyota', model: 'Corolla', price: 29600, electric: false },
  ];

  // Column Definitions: Defines the columns to be displayed.
  colDefs: ColDef[] = [
    { field: 'make' },
    { field: 'model' },
    { field: 'price' },
    { field: 'electric' },
    {
      field: 'actions',
      cellRenderer: ButtonComponent,
      flex: 1,
      onCellClicked:(event)=> this.deleteRow(event.data),
    },
  ];
}
