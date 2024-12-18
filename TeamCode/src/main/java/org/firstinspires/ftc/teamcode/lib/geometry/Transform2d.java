package org.firstinspires.ftc.teamcode.lib.geometry;

/** Represents a transformation for a Pose2d. */
public class Transform2d {
    private final Translation2d m_translation;
    private final Rotation2d m_rotation;

    /**
     * Constructs the transform that maps the initial pose to the final pose.
     *
     * @param initial The initial pose for the transformation.
     * @param last    The final pose for the transformation.
     */
    public Transform2d(Pose2d initial, Pose2d last) {
        // We are rotating the difference between the translations
        // using a clockwise rotation matrix. This transforms the global
        // delta into a local delta (relative to the initial pose).
        m_translation =
                last.getTranslation()
                        .minus(initial.getTranslation())
                        .rotateBy(initial.getRotation().unaryMinus());

        m_rotation = last.getRotation().minus(initial.getRotation());
    }

    /**
     * Constructs a transform with the given translation and rotation components.
     *
     * @param translation Translational component of the transform.
     * @param rotation    Rotational component of the transform.
     */
    public Transform2d(Translation2d translation, Rotation2d rotation) {
        m_translation = translation;
        m_rotation = rotation;
    }

    /**
     * Constructs the identity transform -- maps an initial pose to itself.
     */
    public Transform2d() {
        m_translation = new Translation2d();
        m_rotation = new Rotation2d();
    }

    /**
     * Multiplies the transform by the scalar.
     *
     * @param scalar The scalar.
     * @return The scaled Transform2d.
     */
    public Transform2d times(double scalar) {
        return new Transform2d(m_translation.times(scalar), m_rotation.times(scalar));
    }

    /**
     * Divides the transform by the scalar.
     *
     * @param scalar The scalar.
     * @return The scaled Transform2d.
     */
    public Transform2d div(double scalar) {
        return times(1.0 / scalar);
    }

    /**
     * Composes two transformations.
     *
     * @param other The transform to compose with this one.
     * @return The composition of the two transformations.
     */
    public Transform2d plus(Transform2d other) {
        return new Transform2d(new Pose2d(), new Pose2d().transformBy(this).transformBy(other));
    }

    /**
     * Returns the translation component of the transformation.
     *
     * @return The translational component of the transform.
     */
    public Translation2d getTranslation() {
        return m_translation;
    }

    /**
     * Returns the X component of the transformation's translation.
     *
     * @return The x component of the transformation's translation.
     */
    public double getX() {
        return m_translation.getX();
    }

    /**
     * Returns the Y component of the transformation's translation.
     *
     * @return The y component of the transformation's translation.
     */
    public double getY() {
        return m_translation.getY();
    }

    /**
     * Returns the rotational component of the transformation.
     *
     * @return Reference to the rotational component of the transform.
     */
    public Rotation2d getRotation() {
        return m_rotation;
    }

    /**
     * Invert the transformation. This is useful for undoing a transformation.
     *
     * @return The inverted transformation.
     */
    public Transform2d inverse() {
        // We are rotating the difference between the translations
        // using a clockwise rotation matrix. This transforms the global
        // delta into a local delta (relative to the initial pose).
        return new Transform2d(
                getTranslation().unaryMinus().rotateBy(getRotation().unaryMinus()),
                getRotation().unaryMinus());
    }

    @Override
    public String toString() {
        return String.format("Transform2d(%s, %s)", m_translation, m_rotation);
    }
}
