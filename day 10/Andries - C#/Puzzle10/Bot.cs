using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Puzzle10
{
    public class Bot : IReceiver
    {
        public int Id { get; }
        public List<int> Chips;
        public List<Instruction> Instructions { get; }
        public int InstructionIndex;

        public Bot(int id)
        {
            Id = id;
            InstructionIndex = 0;
            Chips = new List<int>();
        }

        public bool CanInsertChip()
        {
            return Chips.Count < 2;
        }

        public bool CanGiveChips(IReceiver low, IReceiver high)
        {
            return Chips.Count == 2 && low.CanInsertChip() && high.CanInsertChip();
        }

        public void InsertChip(int chipNumber)
        {
            Chips.Add(chipNumber);
            if (Chips.Count > 2)
            {
                throw new ApplicationException("chipcount exceeds 2");
            }
        }

        public void GiveChips(IReceiver low, IReceiver high)
        {
            printGiveChips(this.Id, low.Id, high.Id, Chips.Min(), Chips.Max());
            low.InsertChip(this.Chips.Min());
            high.InsertChip(this.Chips.Max());
            this.Chips = new List<int>();
        }

        private void printGiveChips(int fromBot, int botIdLow, int botIdHigh, int chipLow, int chipHigh)
        {
//            Console.WriteLine("===========================");
//            Console.WriteLine($"Bot {fromBot} gives {chipLow} to receiver {botIdLow}");
//            Console.WriteLine($"Bot {fromBot} gives {chipHigh} to receiver {botIdHigh}");

            if (chipLow == 17 && chipHigh == 61)
            {
                Console.WriteLine($"PART 1 ==> Bot nr {fromBot}");
            }
        }
    }
}
