using System;
using System.Collections.Generic;

namespace Jeroen.Day1
{
    public enum Bearing
    {
        N = 0, E, S, W
    }
    public enum Direction
    {
        L, R
    }

    class Compass
    {
        Bearing _bearing;
        public Compass() : this(Bearing.N) { }
        public Compass(Bearing bearing)
        {
            this._bearing = bearing;
        }

        public Bearing Bearing => _bearing;
        public void Turn(Direction direction)
        {
            _bearing += direction == Direction.R ? 1 : -1;
            if (_bearing > Bearing.W) _bearing = Bearing.N;
            if (_bearing < Bearing.N) _bearing = Bearing.W;
        }
    }

    class Navigator
    {
        private readonly Compass _compass = new Compass();

        private (int x,int y) _position;

        public int Blocks => Math.Abs(_position.x) + Math.Abs(_position.y);
        public void Head(Direction direction, int distance)
        {
            _compass.Turn(direction);

            switch (_compass.Bearing)
            {
                case Bearing.N:
                    _position = (_position.x, _position.y + distance);
                    break;
                case Bearing.E:
                    _position = (_position.x + distance, _position.y);
                    break;
                case Bearing.S:
                    _position = (_position.x, _position.y - distance);
                    break;
                case Bearing.W:
                    _position = (_position.x - distance, _position.y);
                    break;
            }
        }

    }

    static class Extensions
    {
        public static IEnumerable<(Direction, int)> Parse(this string input)
        {
            foreach (var item in input.Split(','))
            {
                var step = item.Trim();
                if (Enum.TryParse(step.Substring(0, 1), out Direction direction) 
                    && int.TryParse(step.Substring(1), out int distance))
                    yield return (direction, distance);
            }
        }
    }
}