import { Component, OnInit } from '@angular/core';

interface Znacznik {
  dlugosc: number;
  szerokosc: number;
  opis?: string;
  przeciaganie: boolean;
}

@Component({
  selector: 'jhi-sklepy',
  templateUrl: './sklepy.component.html'
})
export class SklepyComponent implements OnInit {
  przyblizenie = 11;
  dlugoscStart = 21.006482;
  szerokoscStart = 52.23067;
  typMapy = 'hybrid';

  constructor() {}

  ngOnInit() {}

  znaczniki: Znacznik[] = [
    {
      dlugosc: 20.983412233192237,
      szerokosc: 52.25759088368765,
      opis: 'A',
      przeciaganie: false
    },
    {
      dlugosc: 20.99262150087999,
      szerokosc: 52.232216647855005,
      opis: 'B',
      przeciaganie: false
    }
  ];
}
