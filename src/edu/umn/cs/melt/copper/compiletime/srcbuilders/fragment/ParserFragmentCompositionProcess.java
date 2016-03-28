package edu.umn.cs.melt.copper.compiletime.srcbuilders.fragment;

import edu.umn.cs.melt.copper.compiletime.logging.CompilerLevel;
import edu.umn.cs.melt.copper.compiletime.logging.CompilerLogger;
import edu.umn.cs.melt.copper.compiletime.logging.messages.GenericMessage;
import edu.umn.cs.melt.copper.compiletime.logging.messages.TimingMessage;
import edu.umn.cs.melt.copper.compiletime.pipeline.AuxiliaryMethods;
import edu.umn.cs.melt.copper.compiletime.pipeline.ParserFragments;
import edu.umn.cs.melt.copper.compiletime.pipeline.SourceBuilder;
import edu.umn.cs.melt.copper.compiletime.pipeline.SourceBuilderParameters;
import edu.umn.cs.melt.copper.compiletime.srcbuilders.single.ParserFragmentEngineBuilder;
import edu.umn.cs.melt.copper.main.ParserCompilerParameters;
import edu.umn.cs.melt.copper.runtime.logging.CopperException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;

/**
 * @author Kevin Viratyosin
 */
public class ParserFragmentCompositionProcess implements SourceBuilder<ParserFragments> {
    public ParserFragmentCompositionProcess(ParserCompilerParameters args) {
    }

    @Override
    public int buildSource(ParserFragments fragments, SourceBuilderParameters args) throws CopperException {
        CompilerLogger logger = AuxiliaryMethods.getOrMakeLogger(args);

        PrintStream out;
        if (args.getOutputType() == null) {
            out = null;
        } else {
            switch(args.getOutputType()) {
                case FILE:
                    try {
                        out = new PrintStream(args.getOutputFile());
                    } catch(FileNotFoundException ex) {
                        logger.logError(new GenericMessage(CompilerLevel.QUIET,"Output file " + args.getOutputFile() + " could not be opened for writing",true,false));
                        return 2;
                    }
                    break;
                case STREAM:
                    out = args.getOutputStream();
                    break;
                default:
                    out = null;
            }
        }

        if(out != null)
        {
            long timeBefore;

            // TODO initialize appropriately
            String packageDecl = ""; // ((c.packageDecl == null || c.packageDecl.equals("")) ? "" : "package " + c.packageDecl + ";")
            String importDecls = ""; // ""
            String parserName = ""; // c.parserName
            String scannerName = parserName + "Scanner";
            String parserAncillaries = ""; // "public " + c.parserName + "() {}\n\n"
            String scannerAncillaries = ""; // ""

            ParserFragmentEngineBuilder engineBuilder = new ParserFragmentEngineBuilder(fragments);
            try {
                timeBefore = System.currentTimeMillis();
                engineBuilder.buildEngine(out, packageDecl, importDecls, parserName, scannerName, parserAncillaries, scannerAncillaries);
                if(logger.isLoggable(TimingMessage.TIMING_LEVEL)) logger.log(new TimingMessage("Generating parser code",System.currentTimeMillis() - timeBefore));
                logger.flush();
            } catch (IOException ex) {
                if(logger.isLoggable(CompilerLevel.VERY_VERBOSE)) ex.printStackTrace(System.err);
                logger.logError(new GenericMessage(CompilerLevel.QUIET,"I/O error in code generation: " + ex.getMessage(),true,true));
                return 1;
            }
        }

        logger.flush();

        return 0;
    }

    @Override
    public Set<String> getCustomSwitches() {
        return null;
    }

    @Override
    public String customSwitchUsage() {
        return null;
    }

    @Override
    public int processCustomSwitch(ParserCompilerParameters args, String[] cmdline, int index) {
        return 0;
    }
}
