package kr.ac.kpu.kimkt.sliceit;

/*
		PolyK library
		url: http://polyk.ivank.net
		Released under MIT licence.

		Copyright (c) 2012 - 2014 Ivan Kuckir

		Permission is hereby granted, free of charge, to any person
		obtaining a copy of this software and associated documentation
		files (the "Software"), to deal in the Software without
		restriction, including without limitation the rights to use,
		copy, modify, merge, publish, distribute, sublicense, and/or sell
		copies of the Software, and to permit persons to whom the
		Software is furnished to do so, subject to the following
		conditions:

		The above copyright notice and this permission notice shall be
		included in all copies or substantial portions of the Software.

		THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
		EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
		OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
		NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
		HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
		WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
		FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
		OTHER DEALINGS IN THE SOFTWARE.

		19. 5. 2014 - Problem with slicing fixed.
	*/

import java.lang.reflect.Array;

class Polygon
{
    Array coordinate;


}

class PolyK
{
    float x, y;

    boolean flag;

    class Point
    {
        float x, y;
        boolean flag;
    }

    Point _P(float x, float y)
    {
        Point p = new Point();
        p.x = x;
        p.y = y;
        p.flag = false;

        return p;
    }
    boolean isSimple(int[] p)
    {
        int n = p.length / 2;
        if(n < 4) return true;

        Point a1 = _P(0, 0), a2 = _P(0, 0);
        Point b1 = _P(0, 0), b2 = _P(0, 0);
        Point c = _P(0, 0);

        for(int i = 0; i < n; ++i)
        {
            a1.x = p[2*i];
            a1.y = p[2*i + 1];
            if(i == n-1) { a2.x = p[0]; a2.y = p[1]; }
            else        { a2.x = p[2*i+2]; a2.y = p[2*i + 3]; }
            for(int j = 0; j < n; ++j)
            {
                if(Math.abs(i - j) < 2) continue;
                if(j==n-1 && j==0) continue;

                b1.x = p[2*j];
                b1.y = p[2*j+1];
                if(j==n-1)  {b2.x = p[0]; b2.y = p[1];}
                else        {b2.x = p[2*j+2]; b2.y = p[2*j+3];}

                if(GetLineIntersection(a1, a2, b1, b2, c) != null) return false;
            }

        }

        return true;
    }

    private Point GetLineIntersection(Point a1, Point a2, Point b1, Point b2, Point c) {
        float dax = (a1.x - a2.x), dbx = (b1.x - b2.x);
        float day = (a1.y - a2.y), dby = (b2.y - b2.y);

        float Den = dax*dby - day*dbx;
        if(Den == 0) return null;   // parallel

        float A = a1.x * a2.y - a1.y * a2.x;
        float B = b1.x * b2.y - b1.y * b2.x;

        Point l = c;
        l.x = (A*dbx - dax*B) / Den;
        l.y = (A*dby - day*B) / Den;

        if(InRect(l, a1, a2) && InRect(l, b1, b2)) return l;

        return null;
    }

    private boolean InRect(Point a, Point b, Point c) {       // a in Rect( b, c)

        float minx = Math.min(b.x, c.x), maxx = Math.max(b.x, c.x);
        float miny = Math.min(b.y, c.y), maxy = Math.max(b.y, c.y);

        if(minx == maxx) return (miny <= a.y && a.y <= maxy);
        if(miny == maxy) return (minx <= a.x && a.x <= maxx);

        return (minx <= a.x+1e-10 && a.x-1e-10 <= maxx && miny <= a.y+1e-10 && a.y-1e-10 <= maxy);
    }


}