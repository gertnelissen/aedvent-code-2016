using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Puzzle10
{
    public class Output : IReceiver
    {
        public int Id { get; }
        public List<int> Chips { get; }

        public Output(int id)
        {
            Id = id;
            Chips = new List<int>();
        }


        public void InsertChip(int chipNumber)
        {
            Chips.Add(chipNumber);
            Console.WriteLine($"PART 2 ==> Output {Id} -> Chip: {chipNumber}");
        }

        public bool CanInsertChip()
        {
            return true;
        }
    }
}
