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

  ngOnInit(): void {
    this.urlservice.getUrls().subscribe((res) => (this.rowData = res));
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
        error: (err) =>
          this.snackBar.open('error creating url', undefined, {
            duration: 1500,
            panelClass: ['snack-bar-red'],
          }),
        complete: () => this.resetForm(),
      });

    this.closeForm();
  }

  resetForm() {
    this.snackBar.open('successfully created url', undefined, {
      duration: 1500,
      panelClass: ['snack-bar-green'],
    }),
      (this.longUrl = '');
    this.shortUrl = '';
  }

  rowData: Array<any> = [];

  // Column Definitions: Defines the columns to be displayed.
  colDefs: ColDef[] = [
    { field: 'id' },
    { field: 'title' },
    { field: 'body' },
    { field: 'userId' },
    {
      field: 'actions',
      cellRenderer: ButtonComponent,
      flex: 1,
      onCellClicked: (event) => this.deleteRow(event.data),
    },
  ];
}
