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
import java.util.ArrayList;

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
    boolean isSimple(ArrayList<Float> polygon)
    {
        int n = polygon.size() / 2;
        if(n < 4) return true;

        Point a1 = _P(0, 0), a2 = _P(0, 0);
        Point b1 = _P(0, 0), b2 = _P(0, 0);
        Point c = _P(0, 0);

        for(int i = 0; i < n; ++i)
        {
            a1.x = polygon.get(2*i);
            a1.y = polygon.get(2 * i + 1);
            if(i == n-1) { a2.x = polygon.get(0); a2.y = polygon.get(1); }
            else        { a2.x = polygon.get(2 * i+2); a2.y = polygon.get(2 * i+3); }
            for(int j = 0; j < n; ++j)
            {
                if(Math.abs(i - j) < 2) continue;
                if(j==n-1 && j==0) continue;

                b1.x = polygon.get(2 * j);
                b1.y = polygon.get(2 * j+1);
                if(j==n-1)  {b2.x = polygon.get(0); b2.y = polygon.get(1);}
                else        {b2.x = polygon.get(2 * j+2); b2.y = polygon.get(2 * j+3);}

                if(GetLineIntersection(a1, a2, b1, b2, c) != null) return false;
            }

        }

        return true;
    }
    boolean isConvex(ArrayList<Float> polygon)
    {
        if(polygon.size()<6) return false;
        int l = polygon.size() - 4;
        for(int i = 0; i < l; i+=2)
        {
            if(!convex(polygon.get(i), polygon.get(i + 1), polygon.get(i + 2), polygon.get(i + 3), polygon.get(i + 4), polygon.get(i + 5))) return false;
        }
        if(convex(polygon.get(l), polygon.get(l + 1), polygon.get(l + 2), polygon.get(l + 3), polygon.get(0), polygon.get(1))) return false;
        if(convex(polygon.get(l + 2), polygon.get(l + 3), polygon.get(0), polygon.get(1), polygon.get(2), polygon.get(3))) return false;
        return true;
    }
    float GetArea(ArrayList<Float> polygon)
    {
        if(polygon.size() <6) return 0;
        int l = polygon.size() - 2;
        float sum = 0;
        for(int i=0; i<l; i+=2)
            sum += (polygon.get(i + 2) - polygon.get(i)) * (polygon.get(i + 1) + polygon.get(i + 3));
        sum += (polygon.get(0) - polygon.get(l)) * (polygon.get(l + 1) + polygon.get(1));

        return - sum * 0.5f;
    }

    class AABB
    {
        float x, y;
        float width, height;
        AABB(float x, float y, float width, float height)
        {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
    AABB GetAABB(float[] polygon)
    {
        float minx = 9999999;
        float miny = 9999999;
        float maxx = -minx;
        float maxy = -miny;

        for(int i = 0; i < polygon.length; i+=2)
        {
            minx = Math.min(minx, polygon[i]);
            maxx = Math.max(maxx, polygon[i]);
            miny = Math.min(miny, polygon[i+1]);
            maxy = Math.max(maxy, polygon[i+1]);
        }
        return new AABB(minx, miny, maxx-minx, maxy-miny);
    }

    ArrayList<Float> Reverse(ArrayList<Float> polygon)
    {
        ArrayList<Float> np = new ArrayList<Float>();
        int i = 0;
        for(int j = polygon.size()-2; j >= 0; j-=2)
        {
            np.add(polygon.get(j));
            np.add(polygon.get(j+1));
        }
        return np;
    }
    ArrayList<Float> Triangulate(ArrayList<Float> polygon)
    {
        int n = polygon.size() / 2;
        if( n < 3 ) return new ArrayList<Float>();

        ArrayList<Float> tgs = new ArrayList<Float>();
        ArrayList<Integer> avl = new ArrayList<Integer>();

        for(int i = 0; i < n; ++i) avl.add(i);
        int i = 0;
        int al = n;
        while(al > 3)
        {
            int i0 = avl.get((i+0)% al);
            int i1 = avl.get((i+1)% al);
            int i2 = avl.get((i+2)% al);

            float ax = polygon.get(2*i0), ay = polygon.get(2*i0 + 1);
            float bx = polygon.get(2*i1), by = polygon.get(2*i1 + 1);
            float cx = polygon.get(2*i2), cy = polygon.get(2*i2 + 1);

            boolean earFound = false;
            if(convex(ax, ay, bx, by, cx, cy))
            {
                earFound = true;
                for(int j = 0; j < al; j++)
                {
                    int vi = avl.get(j);
                    if(vi == i0 || vi == i1 || vi == i2) continue;
                    if(PointInTriangle(polygon.get(2*vi), polygon.get(2*vi+1), ax, ay, bx, by, cx, cy))
                    {
                        earFound = false;
                        break;
                    }
                }
            }
            if(earFound)
            {
                tgs.add((float)i0);
                tgs.add((float)i1);
                tgs.add((float)i2);
                avl.remove((i+1)% al); // avl.splice((i+1)% al, 1);
                al--;
                i = 0;
            }
            else if(i++ > 3*al) break;      // no convex angles :(

        }
        tgs.add((float)avl.get(0));
        tgs.add((float)avl.get(1));
        tgs.add((float)avl.get(2));

        return tgs;
    }




    private boolean convex(float ax, float ay, float bx, float by, float cx, float cy) {
        return (ay-by)*(cx-bx) + (bx-ax)*(cy-by) >= 0;
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
    private boolean PointInTriangle(Float px, Float py, float ax, float ay, float bx, float by, float cx, float cy) {
        float v0x = cx-ax;
        float v0y = cy-ay;
        float v1x = bx-ax;
        float v1y = by-ay;
        float v2x = px-ax;
        float v2y = py-ay;

        float dot00 = v0x*v0x+v0y*v0y;
        float dot01 = v0x*v1x+v0y*v1y;
        float dot02 = v0x*v2x+v0y*v2y;
        float dot11 = v1x*v1x+v1y*v1y;
        float dot12 = v1x*v2x+v1y*v2y;

        float invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
        float u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        float v = (dot00 * dot12 - dot01 * dot02) * invDenom;

        // Check if point is in triangle
        return (u >= 0) && (v >= 0) && (u + v < 1);

    }
    private boolean InRect(Point a, Point b, Point c) {       // a in Rect( b, c)

        float minx = Math.min(b.x, c.x), maxx = Math.max(b.x, c.x);
        float miny = Math.min(b.y, c.y), maxy = Math.max(b.y, c.y);

        if(minx == maxx) return (miny <= a.y && a.y <= maxy);
        if(miny == maxy) return (minx <= a.x && a.x <= maxx);

        return (minx <= a.x+1e-10 && a.x-1e-10 <= maxx && miny <= a.y+1e-10 && a.y-1e-10 <= maxy);
    }


}