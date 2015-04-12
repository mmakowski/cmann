package com.mmakowski.cmann;

import com.mmakowski.cmann.assembly.CmAnnAssembly;
import com.mmakowski.cmann.text.StreamInputReader;
import com.mmakowski.cmann.text.StreamOutputWriter;
import com.mmakowski.util.SystemClock;

public final class CmAnnApp {
    private CmAnnApp() {}

    public static void main(final String[] args) {
        final CmAnnAssembly assembly = new CmAnnAssembly(new StreamInputReader(System.in), new StreamOutputWriter(System.out), new SystemClock());
        assembly.repl.run();
    }
}
