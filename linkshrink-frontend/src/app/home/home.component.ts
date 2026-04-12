import { Component, OnInit } from '@angular/core';
import { NavComponent } from '../sub-components/nav/nav.component';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef } from 'ag-grid-community';
import { ButtonComponent } from '../sub-components/grid-components/button/button.component';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { UrlService } from '../services/url.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { debounceTime, Subscription } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NavComponent,
    AgGridAngular,
    MatIconModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
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

  suggestions:string[] = []

  subscription: Subscription = new Subscription()

  ngOnInit(): void {
    this.urlservice.getUrls().subscribe((res) => (this.rowData = res.urls));
  }
  showForm = false;

  urlForm = new FormGroup({
    longUrl: new FormControl<string>('', []),
    shortUrl: new FormControl<string>('', []),
  });

  deleteRow(data: any) {
    this.rowData = this.rowData.filter((d) => d.id !== data.id);
  }

  closeForm() {
    console.log('close called');
    this.showForm = false;
    this.subscription.unsubscribe()

  }
  openForm() {
    this.showForm = true;
    this.subscription = this.urlForm.get('longUrl')!.valueChanges.subscribe((res) => {
      debounceTime(5000)
      if (res) {
        this.urlservice.getSuggestion(res).subscribe((resp) => {
          this.suggestions = resp.suggestions;
        });
      }
    });
  }

  addUrl() {

    console.log(this.urlForm.get('longUrl')?.value??"");
    this.urlservice
      .createUrl({
        longUrl: this.urlForm.get("longUrl")?.value??"",
        shortUrl: this.urlForm.get("shortUrl")?.value??"",
      })
      .subscribe({
        next: (val) => this.resetForm(val),
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
      this.urlForm.reset()
  }

  onSuggestionClick(sugg: string){
    this.urlForm.get('shortUrl')?.setValue(sugg)
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
