package com.github.zxh.classpy.dexfile.bytecode;

import com.github.zxh.classpy.common.FileParseException;
import com.github.zxh.classpy.dexfile.DexComponent;
import com.github.zxh.classpy.dexfile.DexReader;
import com.github.zxh.classpy.dexfile.bytecode.InstructionSet.InstructionInfo;

/**
 *
 * @author zxh
 */
public class Instruction extends DexComponent {

    @Override
    protected void readContent(DexReader reader) {
        int opcode = reader.readUByte();
        int operand;
        int a, b, aa, aaaa, bbbb;
        
        InstructionInfo insnInfo = InstructionSet.getInstructionInfo(opcode);
        switch (insnInfo.format) {
            case _00x:
                reader.readByte();
                setName(insnInfo.simpleMnemonic);
                break;
            case _10x: // op
                reader.readByte();
                setName(insnInfo.simpleMnemonic);
                break;
            case _12x: // op vA, vB
                operand = reader.readUByte();
                a = operand & 0b1111;
                b = operand >> 4;
                setName(insnInfo.simpleMnemonic + " v" + a + ", v" + b);
                break;
            case _11n: // const/4 vA, #+B
                operand = reader.readUByte();
                a = operand & 0b1111;
                b = operand >> 4; // todo
                setName(insnInfo.simpleMnemonic + " v" + a + ", #+" + b);
                break;
            case _11x: // op vAA
                aa = reader.readUByte();
                setName(insnInfo.simpleMnemonic + " v" + aa);
                break;
            case _10t: // op +AA
                aa = reader.readByte();
                setName(insnInfo.simpleMnemonic + " +" + aa);
                break;
            case _20t: // op +AAAA
                reader.readByte();
                aaaa = reader.readShort();
                setName(insnInfo.simpleMnemonic + " +" + aaaa);
                break;
            case _20bc: // todo
                reader.readByte();
                reader.readUShort();
                setName(insnInfo.simpleMnemonic);
                break;
            case _22x: // op vAA, vBBBB
                aa = reader.readUByte();
                bbbb = reader.readUShort().getValue();
                setName(insnInfo.simpleMnemonic + " v" + aa + ", v" + bbbb);
                break;
            case _21t: // op vAA, +BBBB
                // todo
            case _21s:
            case _21h:
            case _21c:
            case _23x:
            case _22b:
            case _22t:
            case _22s:
            case _22c:
            case _22cs:
                reader.readByte();
                reader.readUShort();
                setName(insnInfo.simpleMnemonic);
                break;
            default:
                throw new FileParseException("XXX");
        }
    }
    
}
