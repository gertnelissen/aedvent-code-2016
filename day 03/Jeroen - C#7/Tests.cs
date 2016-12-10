using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using Xunit;
using Xunit.Abstractions;

namespace Jeroen
{

    public class Tests
    {
        private ITestOutputHelper output;

        public Tests(ITestOutputHelper output)
        {
            this.output = output;
        }

        [Theory]
        [InlineData(5, 10, 25, false)]
        [InlineData(5, 25, 10, false)]
        [InlineData(25, 5, 10, false)]
        [InlineData(25, 10, 5, false)]
        [InlineData(10, 25, 5, false)]
        [InlineData(10, 5, 25, false)]
        [InlineData(5, 10, 12, true)]
        [InlineData(5, 12, 10, true)]
        [InlineData(12, 5, 10, true)]
        [InlineData(12, 10, 5, true)]
        [InlineData(10, 12, 5, true)]
        [InlineData(10, 5, 12, true)]
        [InlineData(3, 4, 5, true)]
        public void Test(int x, int y, int z, bool expected)
        {
            Assert.Equal(IsPossibleTriangle(x, y, z), expected);
        }

        public bool IsPossibleTriangle(int x, int y, int z)
        {
            return x + y > z
                && y + z > x
                && x + z > y;
        }

        IEnumerable<string> ReadInputs()
        {
            var stream = Assembly.GetExecutingAssembly().GetManifestResourceStream("Jeroen.input.txt");
            using (var reader = new StreamReader(stream))
            {
                while (reader.Peek() > 0)
                {
                    yield return reader.ReadLine();
                }
            }

        }

        [Fact]
        public void Day3Part1()
        {
            var triangles = from line in ReadInputs()
                            let x = int.Parse(line.Substring(2,3).Trim())
                            let y = int.Parse(line.Substring(7,3).Trim())
                            let z = int.Parse(line.Substring(12, 3).Trim())
                            where IsPossibleTriangle(x, y, z)
                            select line;
            var count = triangles.Count();
            output.WriteLine(count.ToString());
        }
    }
}
