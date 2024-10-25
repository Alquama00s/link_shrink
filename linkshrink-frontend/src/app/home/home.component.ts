import { Component, OnInit } from '@angular/core';
import { NavComponent } from '../sub-components/nav/nav.component';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef } from 'ag-grid-community';
import { ButtonComponent } from '../sub-components/grid-components/button/button.component';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { UrlService } from '../services/url.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NavComponent,
    AgGridAngular,
    MatIconModule,
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  constructor(private urlservice: UrlService, private snackBar: MatSnackBar) {}

  rowData: Array<any> = [];

  ngOnInit(): void {
    this.urlservice.getUrls().subscribe((res) => (this.rowData = res.urls));
  }
  showForm = false;
  longUrl = '';
  shortUrl = '';

  deleteRow(data: any) {
    this.rowData = this.rowData.filter((d) => d.id !== data.id);
  }

  closeForm() {
    this.showForm = false;
  }
  openForm() {
    this.showForm = true;
  }

  addUrl() {
    this.urlservice
      .createUrl({
        longUrl: this.longUrl,
        shortUrl: this.shortUrl,
      })
      .subscribe({
        next: (val) => this.resetForm(val),
        error: (err) =>
          this.snackBar.open('error creating url', undefined, {
            duration: 1500,
            panelClass: ['snack-bar-red'],
          }),
      });

    this.closeForm();
  }

  resetForm(val: any) {
    let nData = structuredClone(this.rowData);
    nData.push(val);
    this.rowData = nData;
    this.snackBar.open('successfully created url', undefined, {
      duration: 1500,
      panelClass: ['snack-bar-green'],
    }),
      (this.longUrl = '');
    this.shortUrl = '';
  }

  // Column Definitions: Defines the columns to be displayed.
  colDefs: ColDef[] = [
    { field: 'id', maxWidth: 100 },
    { field: 'longUrl', minWidth: 800 },
    { field: 'shortUrl' },
    { field: 'expiryAfter', minWidth: 300 },
    {
      field: 'actions',
      cellRenderer: ButtonComponent,
      flex: 1,
      onCellClicked: (event) => this.deleteRow(event.data),
    },
  ];
}
