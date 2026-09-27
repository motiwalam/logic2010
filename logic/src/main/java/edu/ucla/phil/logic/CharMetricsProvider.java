package edu.ucla.phil.logic;

import java.awt.FontMetrics;

interface CharMetricsProvider {
   FontMetrics getMetrics();

   int getCharWidth(char c0);
}
