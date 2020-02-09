import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { PrzepisService } from 'app/entities/przepis/przepis.service';
import { IPrzepis, Przepis } from 'app/shared/model/przepis.model';
import { TypPrzepisuEnum } from 'app/shared/model/enumerations/typ-przepisu-enum.model';

describe('Service Tests', () => {
  describe('Przepis Service', () => {
    let injector: TestBed;
    let service: PrzepisService;
    let httpMock: HttpTestingController;
    let elemDefault: IPrzepis;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PrzepisService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Przepis(0, 'AAAAAAA', TypPrzepisuEnum.WEGETARIANSKI, 'image/png', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Przepis', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Przepis(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Przepis', () => {
        const returnedFromService = Object.assign(
          {
            nazwa: 'BBBBBB',
            typPrzepisu: 'BBBBBB',
            zdjecie: 'BBBBBB',
            opis: 'BBBBBB',
            kalorieSuma: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Przepis', () => {
        const returnedFromService = Object.assign(
          {
            nazwa: 'BBBBBB',
            typPrzepisu: 'BBBBBB',
            zdjecie: 'BBBBBB',
            opis: 'BBBBBB',
            kalorieSuma: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Przepis', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
