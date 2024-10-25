import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule,MatIconModule],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.scss'
})
export class NavComponent {

  // profile:any|null=null

  constructor(private auth:AuthService,private router:Router){
    // auth.profile().subscribe(res=>{
    //   console.log(res)
    //   this.profile = res;
    // });
  }


  profile(){
    this.router.navigate(["web","profile"])
  }
}
