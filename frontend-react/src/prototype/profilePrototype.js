// Simple prototype (client-side) for UI defaults when creating a new profile.
export const defaultProfilePrototype = Object.freeze({
  dailyGoalMinutes: 15,
  streakDays: 0,
  totalMinutes: 0,
  totalXp: 0,
  level: 1,
  cefrLevel: 'A2'
});

export function cloneProfile(overrides = {}) {
  // Return a shallow clone merged with optional overrides
  return { ...defaultProfilePrototype, ...overrides };
}