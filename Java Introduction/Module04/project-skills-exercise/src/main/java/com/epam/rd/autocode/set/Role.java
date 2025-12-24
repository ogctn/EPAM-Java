package com.epam.rd.autocode.set;

import java.util.EnumSet;
import java.util.Set;

public class Role {

	private Level level;
	private Position position;
	private Set<Skill> skills;

	public Role(Position position, Level level, Skill... skills) {
	    this.position = position;
        this.level = level;
        this.skills = EnumSet.noneOf(Skill.class);

        if (skills != null) {
            for (Skill sk : skills) {
                if (sk != null)
                    this.skills.add(sk);
            }
        }
    }

    public Level getLevel() {
        return (level);
    }

    public Position getPosition() {
        return (position);
    }

	public Set<Skill> getSkills() {
		return (EnumSet.copyOf(skills));
	}

}
