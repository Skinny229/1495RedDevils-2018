package org.usfirst.frc.team1495.robot.pathgen;

public class Path {
	public final CurveSegment[] segments;

	public Path(CurveSegment... ss) {
		segments = ss;
	}

	public boolean isValid() {
		CurveSegment prevSegment = null;
		for(CurveSegment s : segments) {
			if(prevSegment == null) 
				prevSegment = s;
			else if (!CurveSegment.Continuity1(prevSegment, s)){
				return false;
			}
		}
		return true;
	}
}
