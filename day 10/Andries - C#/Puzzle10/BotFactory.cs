using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Puzzle10
{
    public class BotFactory
    {
        private List<Bot> _botList;
        private List<Output> _outputList;
        private List<Instruction> _instructions;

        public BotFactory(List<string> rawInstructions)
        {
            _outputList = new List<Output>();
            _botList = new List<Bot>();
            _instructions = new List<Instruction>();
            ParseInstructionList(rawInstructions);
            BuildFactory();
        }

        public void RunFactory()
        {
            while (!_instructions.All(i => i.IsExecuted))
            {
                IterateOneSequence();
            }
        }

        private void IterateOneSequence()
        {
            foreach (var instruction in _instructions)
            {
                if (!instruction.IsExecuted)
                {
                    var isExecuted = ExecuteInstruction(instruction);
                    if (isExecuted)
                    {
                        break;
                    }
                }
            }
        }

        private bool ExecuteInstruction(Instruction i)
        {
            Bot bot = _botList.Find(b => b.Id == i.BotId);
            if (bot == null)
            {
                throw new ArgumentException("no bot found");
            }

            switch (i.InstructionType)
            {
                case InstructionType.BotGives:
                    IReceiver low = GetReceiver(i.LowToId, i.LowToType);
                    IReceiver high = GetReceiver(i.HighToId, i.HighToType);
                    if (bot.CanGiveChips(low, high))
                    {
                        bot.GiveChips(low, high);
                        i.IsExecuted = true;
                        return true;
                    }
                    return false;
                case InstructionType.BotGets:
                    if (bot.CanInsertChip())
                    {
                        bot.InsertChip(i.GetValue);
                        i.IsExecuted = true;
                        return true;
                    }
                    return false;
                default:
                    throw new ArgumentOutOfRangeException();
            }
        }

        private IReceiver GetReceiver(int id, ReceiverType type)
        {
            switch (type)
            {
                case ReceiverType.bot:
                    return _botList.Find(b => b.Id == id);
                case ReceiverType.output:
                    return _outputList.Find(o => o.Id == id);
                default:
                    throw new ArgumentOutOfRangeException(nameof(type), type, null);
            }
        }

        private void BuildFactory()
        {
            foreach (var i in _instructions)
            {
                // create bots & outputs if not exists yet
                if (GetReceiver(i.BotId, ReceiverType.bot) == null)
                {
                    CreateBot(i.BotId);
                }
                if (GetReceiver(i.LowToId, ReceiverType.bot) == null && i.LowToType == ReceiverType.bot)
                {
                    CreateBot(i.LowToId);
                }
                if (GetReceiver(i.HighToId, ReceiverType.bot) == null && i.HighToType == ReceiverType.bot)
                {
                    CreateBot(i.HighToId);
                }
                if (GetReceiver(i.LowToId, ReceiverType.output) == null && i.LowToType == ReceiverType.output)
                {
                    CreateOutput(i.LowToId);
                }
                if (GetReceiver(i.HighToId, ReceiverType.output) == null && i.HighToType == ReceiverType.output)
                {
                    CreateOutput(i.HighToId);
                }
            }
        }

        private void ParseInstructionList(List<string> rawInstructions)
        {
            foreach (var rawInstruction in rawInstructions)
            {
                _instructions.Add(new Instruction(rawInstruction));
            }
        }

        private void CreateBot(int id)
        {
            _botList.Add(new Bot(id));
        }

        private void CreateOutput(int id)
        {
            _outputList.Add(new Output(id));
        }
    }
}
