import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token')
  if(!token || token.trim().length==0) return next(req)
  const modifiedReq = req.clone({
    setHeaders: {
      Authorization: 'Bearer ' + token
    },
  });
  return next(modifiedReq);
};
