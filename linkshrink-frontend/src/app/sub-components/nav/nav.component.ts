import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.scss'
})
export class NavComponent {

  profile:any|null=null

  constructor(private auth:AuthService){
    auth.profile().subscribe(res=>{
      console.log(res)
      this.profile = res;
    });
  }
}
