using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Puzzle10
{
    public enum InstructionType
    {
        BotGives,
        BotGets
    }

    public enum ReceiverType
    {
        bot,
        output
    }

    public class Instruction
    {
        private string _input;
        public InstructionType InstructionType { get; set; }
        public int BotId { get; set; }
        public int LowToId { get; set; }
        public ReceiverType LowToType { get; set; }
        public int HighToId { get; set; }
        public ReceiverType HighToType { get; set; }
        public int GetValue { get; set; }

        public bool IsExecuted { get; set; }

        /// <summary>
        /// value 61 goes to bot 209
        /// bot 200 gives low to bot 40 and high to bot 141
        /// bot 197 gives low to bot 202 and high to bot 65
        /// bot 19 gives low to bot 170 and high to bot 120
        /// bot 1 gives low to output 1 and high to bot 0
        /// bot 0 gives low to output 2 and high to output 0
        /// </summary>
        /// <param name="line"></param>
        public Instruction(string input)
        {
            _input = input;
            IsExecuted = false;
            ParseInput(input);
        }

        private void ParseInput(string line)
        {
            string pattern1 = @"^bot\s(.+)\sgives\slow\sto\s(.+)\s(.+)\sand\shigh\sto\s(.+)\s(.+)$";
            string pattern2 = @"^value\s(.+)\sgoes\sto\sbot\s(.+)$";
            Match match1 = Regex.Match(line, pattern1);
            Match match2 = Regex.Match(line, pattern2);

            if (match1.Success)
            {
                InstructionType = InstructionType.BotGives;
                ParseBotGives(match1);
            } else if (match2.Success)
            {
                InstructionType = InstructionType.BotGets;
                ParseBotGets(match2);
            }
        }

        private void ParseBotGives(Match match)
        {
            var gr = match.Groups;
            BotId = Int32.Parse(match.Groups[1].Value);

            ReceiverType LowTo;
            Enum.TryParse(match.Groups[2].Value, out LowTo);
            LowToType = LowTo;

            LowToId = Int32.Parse(match.Groups[3].Value);

            ReceiverType HighTo;
            Enum.TryParse(match.Groups[4].Value, out HighTo);
            HighToType = HighTo;

            HighToId = Int32.Parse(match.Groups[5].Value);
        }

        private void ParseBotGets(Match match)
        {
            GetValue = Int32.Parse(match.Groups[1].Value);
            BotId = Int32.Parse(match.Groups[2].Value);
        }
    }
}
