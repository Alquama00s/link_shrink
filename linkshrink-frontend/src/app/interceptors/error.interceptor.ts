import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const snackBar = inject(MatSnackBar);
  return next(req).pipe(
    catchError((err) => {
      console.log(err)
      const msg = err.error.message ?? 'something went wrong';
      snackBar.open(msg, undefined, {
        duration: 1500,
        panelClass: ['snack-bar-red'],
      });
      return throwError(() => err);
    })
  );
};
