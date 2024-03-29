import { AuthService } from './auth.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Application } from '../models/application';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  private url = environment.baseUrl + 'api/applications';

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) { }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
          'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

  public index(): Observable<Application[]> {
    return this.http.get<Application[]>(this.url, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        return throwError(
          () => new Error('ApplicationService.index(): error retrieving Applications: ' + err)
        );
      })
    );
  }

  public create(id: number, application: Application): Observable<Application> {
    return this.http.post<Application>(this.url + '/' + id, application, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        return throwError(
          () => new Error('ApplicationService.create(): error creating Application: ' + err)
        );
      })
    );
  }

  public destroy(id: number): Observable<Application> {
    return this.http.delete<Application>(this.url + '/' + id, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        return throwError(
          () => new Error('ApplicationService.destroy(): error deleting Application: ' + err)
          );
        })
    );
  }

}
