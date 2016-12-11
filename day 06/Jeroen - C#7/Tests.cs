using System.Collections.Generic;
using System.Linq;
using System.Text;
using Xunit;
using Xunit.Abstractions;

namespace Jeroen
{

    public class Tests
    {
        ITestOutputHelper output;

        public Tests(ITestOutputHelper output)
        {
            this.output = output;
        }

        [Fact]
        public void Part1Test()
        {
            var lines = InputReader.Test();
            var result = new Accumulator().Decode(lines, 6);
            Assert.Equal("easter", result);
        }

    }

    public class Accumulator
    {
        public string Decode(IEnumerable<string> data, int lineLength)
        {
            var query = from line in data
                        from item in line.Select((c, i) => new { c, pos = i })
                        select item;

            var lookup = query.ToLookup(item => item.pos);

            var sb = new StringBuilder();
            for (int i = 0; i < lineLength; i++)
            {
                var g = lookup[i];
                var c = g.GroupBy(item => item.c).OrderByDescending(x => x.Count()).First().First().c;
                sb.Append(c);
            }
            return sb.ToString();
        }
    }
}
